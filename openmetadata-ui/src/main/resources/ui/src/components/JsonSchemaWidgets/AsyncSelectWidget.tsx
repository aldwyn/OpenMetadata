/*
 *  Copyright 2023 Collate.
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *  http://www.apache.org/licenses/LICENSE-2.0
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
import { WidgetProps } from '@rjsf/utils';
import React from 'react';
import { SearchIndex } from '../../enums/search.enum';
import DataAssetAsyncSelectList from '../DataAssetAsyncSelectList/DataAssetAsyncSelectList';
import { DataAssetOption } from '../DataAssetAsyncSelectList/DataAssetAsyncSelectList.interface';

const AsyncSelectWidget = ({ onChange, schema }: WidgetProps) => {
  const handleChange = (value: DataAssetOption | DataAssetOption[]) => {
    const data = value.map((item: DataAssetOption) => item.reference);
    onChange(data);
  };

  return (
    <DataAssetAsyncSelectList
      defaultValue={schema.value}
      mode="multiple"
      placeholder={schema.placeholder ?? ''}
      searchIndex={schema?.autoCompleteType ?? SearchIndex.TABLE}
      onChange={handleChange}
    />
  );
};

export default AsyncSelectWidget;
