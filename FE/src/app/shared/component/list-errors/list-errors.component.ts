import { Component, Input } from '@angular/core';
import { Errors } from '../../../core/models/errors.model';
import { error } from '@angular/compiler-cli/src/transformers/util';
import { NgIf, NgFor } from '@angular/common';

@Component({
  selector: 'app-list-errors',
  templateUrl: './list-errors.component.html',
  standalone: true,
  imports: [NgIf, NgFor],
})
export class ListErrorsComponent {
  errorList: string[] = [];

  @Input() set errors(errorList: Errors | null) {
    this.errorList = errorList
      ? Object.keys(errorList.errors || {}).map(
          key => `${key} ${errorList.errors[key]}`
        )
      : [];
  }

  protected readonly error = error;
}
