import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { SensorModelComponent } from './list/sensor-model.component';
import { SensorModelDetailComponent } from './detail/sensor-model-detail.component';
import { SensorModelUpdateComponent } from './update/sensor-model-update.component';
import { SensorModelDeleteDialogComponent } from './delete/sensor-model-delete-dialog.component';
import { SensorModelRoutingModule } from './route/sensor-model-routing.module';

@NgModule({
  imports: [SharedModule, SensorModelRoutingModule],
  declarations: [SensorModelComponent, SensorModelDetailComponent, SensorModelUpdateComponent, SensorModelDeleteDialogComponent],
  entryComponents: [SensorModelDeleteDialogComponent],
})
export class SensorModelModule {}
