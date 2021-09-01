# generated by datamodel-codegen:
#   filename:  schema/api/services/updateDashboardService.json
#   timestamp: 2021-09-01T06:44:13+00:00

from __future__ import annotations

from typing import Optional

from pydantic import AnyUrl, BaseModel, Field

from ...type import schedule


class UpdateDashboardServiceEntityRequest(BaseModel):
    description: Optional[str] = Field(
        None, description='Description of Dashboard service entity.'
    )
    dashboardUrl: Optional[AnyUrl] = Field(None, description='Dashboard Service URL')
    username: Optional[str] = Field(
        None, description='Username to log-into Dashboard Service'
    )
    password: Optional[str] = Field(
        None, description='Password to log-into Dashboard Service'
    )
    ingestionSchedule: Optional[schedule.Schedule] = Field(
        None, description='Schedule for running metadata ingestion jobs'
    )
