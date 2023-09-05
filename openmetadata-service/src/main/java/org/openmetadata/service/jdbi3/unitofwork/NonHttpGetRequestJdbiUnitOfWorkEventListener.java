package org.openmetadata.service.jdbi3.unitofwork;

import javax.ws.rs.HttpMethod;
import lombok.extern.slf4j.Slf4j;
import org.glassfish.jersey.server.model.ResourceMethod;
import org.glassfish.jersey.server.monitoring.RequestEvent;
import org.glassfish.jersey.server.monitoring.RequestEventListener;

@Slf4j
class NonHttpGetRequestJdbiUnitOfWorkEventListener implements RequestEventListener {

  private final JdbiTransactionAspect transactionAspect;

  NonHttpGetRequestJdbiUnitOfWorkEventListener(JdbiHandleManager handleManager) {
    this.transactionAspect = new JdbiTransactionAspect(handleManager);
  }

  @Override
  public void onEvent(RequestEvent event) {
    RequestEvent.Type type = event.getType();
    String httpMethod = event.getContainerRequest().getMethod();

    LOG.debug("Handling {} Request Event {} {}", httpMethod, type, Thread.currentThread().getId());
    boolean isTransactional = isTransactional(event);
    if (isTransactional) {
      if (type == RequestEvent.Type.RESOURCE_METHOD_START) {
        transactionAspect.begin(false);
      } else if (type == RequestEvent.Type.RESP_FILTERS_FINISHED) {
        transactionAspect.commit();
      } else if (type == RequestEvent.Type.ON_EXCEPTION) {
        transactionAspect.rollback();
      } else if (type == RequestEvent.Type.FINISHED) {
        transactionAspect.terminateHandle();
      }
    }
  }

  private boolean isTransactional(RequestEvent event) {
    ResourceMethod method = event.getUriInfo().getMatchedResourceMethod();
    String httpMethod = event.getContainerRequest().getMethod();
    if (httpMethod.equals(HttpMethod.POST)
        || httpMethod.equals(HttpMethod.PUT)
        || httpMethod.equals(HttpMethod.DELETE)) {
      return true;
    }
    if (method != null) {
      JdbiUnitOfWork annotation = method.getInvocable().getDefinitionMethod().getAnnotation(JdbiUnitOfWork.class);
      return annotation != null;
    }
    return false;
  }
}
