import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { IrrigationComponent } from './list/irrigation.component';
import { IrrigationDetailComponent } from './detail/irrigation-detail.component';
import { IrrigationUpdateComponent } from './update/irrigation-update.component';
import { IrrigationDeleteDialogComponent } from './delete/irrigation-delete-dialog.component';
import { IrrigationRoutingModule } from './route/irrigation-routing.module';

@NgModule({
  imports: [SharedModule, IrrigationRoutingModule],
  declarations: [IrrigationComponent, IrrigationDetailComponent, IrrigationUpdateComponent, IrrigationDeleteDialogComponent],
  entryComponents: [IrrigationDeleteDialogComponent],
})
export class IrrigationModule {}
