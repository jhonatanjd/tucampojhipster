import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IProducer } from '../producer.model';
import { ProducerService } from '../service/producer.service';

@Component({
  templateUrl: './producer-delete-dialog.component.html',
})
export class ProducerDeleteDialogComponent {
  producer?: IProducer;

  constructor(protected producerService: ProducerService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.producerService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
