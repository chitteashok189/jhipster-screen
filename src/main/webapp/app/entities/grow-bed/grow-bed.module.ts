import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { GrowBedComponent } from './list/grow-bed.component';
import { GrowBedDetailComponent } from './detail/grow-bed-detail.component';
import { GrowBedUpdateComponent } from './update/grow-bed-update.component';
import { GrowBedDeleteDialogComponent } from './delete/grow-bed-delete-dialog.component';
import { GrowBedRoutingModule } from './route/grow-bed-routing.module';

@NgModule({
  imports: [SharedModule, GrowBedRoutingModule],
  declarations: [GrowBedComponent, GrowBedDetailComponent, GrowBedUpdateComponent, GrowBedDeleteDialogComponent],
  entryComponents: [GrowBedDeleteDialogComponent],
})
export class GrowBedModule {}
