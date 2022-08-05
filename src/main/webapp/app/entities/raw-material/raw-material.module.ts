import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { RawMaterialComponent } from './list/raw-material.component';
import { RawMaterialDetailComponent } from './detail/raw-material-detail.component';
import { RawMaterialUpdateComponent } from './update/raw-material-update.component';
import { RawMaterialDeleteDialogComponent } from './delete/raw-material-delete-dialog.component';
import { RawMaterialRoutingModule } from './route/raw-material-routing.module';

@NgModule({
  imports: [SharedModule, RawMaterialRoutingModule],
  declarations: [RawMaterialComponent, RawMaterialDetailComponent, RawMaterialUpdateComponent, RawMaterialDeleteDialogComponent],
  entryComponents: [RawMaterialDeleteDialogComponent],
})
export class RawMaterialModule {}
