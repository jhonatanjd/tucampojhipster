import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IOrderDetai } from '../order-detai.model';
import { OrderDetaiService } from '../service/order-detai.service';

@Component({
  templateUrl: './order-detai-delete-dialog.component.html',
})
export class OrderDetaiDeleteDialogComponent {
  orderDetai?: IOrderDetai;

  constructor(protected orderDetaiService: OrderDetaiService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.orderDetaiService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
