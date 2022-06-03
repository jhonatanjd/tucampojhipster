import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { DocumenTypeComponent } from './list/documen-type.component';
import { DocumenTypeDetailComponent } from './detail/documen-type-detail.component';
import { DocumenTypeUpdateComponent } from './update/documen-type-update.component';
import { DocumenTypeDeleteDialogComponent } from './delete/documen-type-delete-dialog.component';
import { DocumenTypeRoutingModule } from './route/documen-type-routing.module';

@NgModule({
  imports: [SharedModule, DocumenTypeRoutingModule],
  declarations: [DocumenTypeComponent, DocumenTypeDetailComponent, DocumenTypeUpdateComponent, DocumenTypeDeleteDialogComponent],
  entryComponents: [DocumenTypeDeleteDialogComponent],
})
export class DocumenTypeModule {}
