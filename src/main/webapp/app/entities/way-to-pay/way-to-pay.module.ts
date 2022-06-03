import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { WayToPayComponent } from './list/way-to-pay.component';
import { WayToPayDetailComponent } from './detail/way-to-pay-detail.component';
import { WayToPayUpdateComponent } from './update/way-to-pay-update.component';
import { WayToPayDeleteDialogComponent } from './delete/way-to-pay-delete-dialog.component';
import { WayToPayRoutingModule } from './route/way-to-pay-routing.module';

@NgModule({
  imports: [SharedModule, WayToPayRoutingModule],
  declarations: [WayToPayComponent, WayToPayDetailComponent, WayToPayUpdateComponent, WayToPayDeleteDialogComponent],
  entryComponents: [WayToPayDeleteDialogComponent],
})
export class WayToPayModule {}
