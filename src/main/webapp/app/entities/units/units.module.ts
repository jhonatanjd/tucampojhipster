import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { UnitsComponent } from './list/units.component';
import { UnitsDetailComponent } from './detail/units-detail.component';
import { UnitsUpdateComponent } from './update/units-update.component';
import { UnitsDeleteDialogComponent } from './delete/units-delete-dialog.component';
import { UnitsRoutingModule } from './route/units-routing.module';

@NgModule({
  imports: [SharedModule, UnitsRoutingModule],
  declarations: [UnitsComponent, UnitsDetailComponent, UnitsUpdateComponent, UnitsDeleteDialogComponent],
  entryComponents: [UnitsDeleteDialogComponent],
})
export class UnitsModule {}
