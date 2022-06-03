import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IShopping } from '../shopping.model';
import { ShoppingService } from '../service/shopping.service';

@Component({
  templateUrl: './shopping-delete-dialog.component.html',
})
export class ShoppingDeleteDialogComponent {
  shopping?: IShopping;

  constructor(protected shoppingService: ShoppingService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.shoppingService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
