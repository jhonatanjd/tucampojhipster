import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { ProducerComponent } from './list/producer.component';
import { ProducerDetailComponent } from './detail/producer-detail.component';
import { ProducerUpdateComponent } from './update/producer-update.component';
import { ProducerDeleteDialogComponent } from './delete/producer-delete-dialog.component';
import { ProducerRoutingModule } from './route/producer-routing.module';

@NgModule({
  imports: [SharedModule, ProducerRoutingModule],
  declarations: [ProducerComponent, ProducerDetailComponent, ProducerUpdateComponent, ProducerDeleteDialogComponent],
  entryComponents: [ProducerDeleteDialogComponent],
})
export class ProducerModule {}
