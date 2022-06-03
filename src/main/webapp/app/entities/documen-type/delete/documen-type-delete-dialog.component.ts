import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IDocumenType } from '../documen-type.model';
import { DocumenTypeService } from '../service/documen-type.service';

@Component({
  templateUrl: './documen-type-delete-dialog.component.html',
})
export class DocumenTypeDeleteDialogComponent {
  documenType?: IDocumenType;

  constructor(protected documenTypeService: DocumenTypeService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.documenTypeService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
