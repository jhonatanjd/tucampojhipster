import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IDats } from '../dats.model';
import { DatsService } from '../service/dats.service';

@Component({
  templateUrl: './dats-delete-dialog.component.html',
})
export class DatsDeleteDialogComponent {
  dats?: IDats;

  constructor(protected datsService: DatsService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.datsService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
