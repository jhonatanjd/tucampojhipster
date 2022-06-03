import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { ShoppingComponent } from './list/shopping.component';
import { ShoppingDetailComponent } from './detail/shopping-detail.component';
import { ShoppingUpdateComponent } from './update/shopping-update.component';
import { ShoppingDeleteDialogComponent } from './delete/shopping-delete-dialog.component';
import { ShoppingRoutingModule } from './route/shopping-routing.module';

@NgModule({
  imports: [SharedModule, ShoppingRoutingModule],
  declarations: [ShoppingComponent, ShoppingDetailComponent, ShoppingUpdateComponent, ShoppingDeleteDialogComponent],
  entryComponents: [ShoppingDeleteDialogComponent],
})
export class ShoppingModule {}
