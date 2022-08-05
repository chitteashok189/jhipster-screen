import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { HarvestComponent } from './list/harvest.component';
import { HarvestDetailComponent } from './detail/harvest-detail.component';
import { HarvestUpdateComponent } from './update/harvest-update.component';
import { HarvestDeleteDialogComponent } from './delete/harvest-delete-dialog.component';
import { HarvestRoutingModule } from './route/harvest-routing.module';

@NgModule({
  imports: [SharedModule, HarvestRoutingModule],
  declarations: [HarvestComponent, HarvestDetailComponent, HarvestUpdateComponent, HarvestDeleteDialogComponent],
  entryComponents: [HarvestDeleteDialogComponent],
})
export class HarvestModule {}
