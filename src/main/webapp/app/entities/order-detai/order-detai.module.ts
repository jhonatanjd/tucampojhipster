import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { OrderDetaiComponent } from './list/order-detai.component';
import { OrderDetaiDetailComponent } from './detail/order-detai-detail.component';
import { OrderDetaiUpdateComponent } from './update/order-detai-update.component';
import { OrderDetaiDeleteDialogComponent } from './delete/order-detai-delete-dialog.component';
import { OrderDetaiRoutingModule } from './route/order-detai-routing.module';

@NgModule({
  imports: [SharedModule, OrderDetaiRoutingModule],
  declarations: [OrderDetaiComponent, OrderDetaiDetailComponent, OrderDetaiUpdateComponent, OrderDetaiDeleteDialogComponent],
  entryComponents: [OrderDetaiDeleteDialogComponent],
})
export class OrderDetaiModule {}
