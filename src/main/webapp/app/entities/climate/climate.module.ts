import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { ClimateComponent } from './list/climate.component';
import { ClimateDetailComponent } from './detail/climate-detail.component';
import { ClimateUpdateComponent } from './update/climate-update.component';
import { ClimateDeleteDialogComponent } from './delete/climate-delete-dialog.component';
import { ClimateRoutingModule } from './route/climate-routing.module';

@NgModule({
  imports: [SharedModule, ClimateRoutingModule],
  declarations: [ClimateComponent, ClimateDetailComponent, ClimateUpdateComponent, ClimateDeleteDialogComponent],
  entryComponents: [ClimateDeleteDialogComponent],
})
export class ClimateModule {}
