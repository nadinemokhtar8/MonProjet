import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'searchFilter',
  standalone: true
})
export class SearchFilterPipe implements PipeTransform {
  transform(list: any[], filterText: string): any {
    if (!list) {
      return [];
    }

    if (!filterText) {
      return list;
    }

    return list.filter(item => item.nomBook.toLowerCase().includes(filterText.toLowerCase()));
  }
}
