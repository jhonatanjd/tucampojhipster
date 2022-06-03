import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IUnits } from '../units.model';
import { UnitsService } from '../service/units.service';

@Component({
  templateUrl: './units-delete-dialog.component.html',
})
export class UnitsDeleteDialogComponent {
  units?: IUnits;

  constructor(protected unitsService: UnitsService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.unitsService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
