import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { DatsComponent } from './list/dats.component';
import { DatsDetailComponent } from './detail/dats-detail.component';
import { DatsUpdateComponent } from './update/dats-update.component';
import { DatsDeleteDialogComponent } from './delete/dats-delete-dialog.component';
import { DatsRoutingModule } from './route/dats-routing.module';

@NgModule({
  imports: [SharedModule, DatsRoutingModule],
  declarations: [DatsComponent, DatsDetailComponent, DatsUpdateComponent, DatsDeleteDialogComponent],
  entryComponents: [DatsDeleteDialogComponent],
})
export class DatsModule {}
