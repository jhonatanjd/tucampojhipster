import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IWayToPay } from '../way-to-pay.model';
import { WayToPayService } from '../service/way-to-pay.service';

@Component({
  templateUrl: './way-to-pay-delete-dialog.component.html',
})
export class WayToPayDeleteDialogComponent {
  wayToPay?: IWayToPay;

  constructor(protected wayToPayService: WayToPayService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.wayToPayService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
