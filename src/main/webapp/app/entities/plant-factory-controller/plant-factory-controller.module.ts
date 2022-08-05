import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { PlantFactoryControllerComponent } from './list/plant-factory-controller.component';
import { PlantFactoryControllerDetailComponent } from './detail/plant-factory-controller-detail.component';
import { PlantFactoryControllerUpdateComponent } from './update/plant-factory-controller-update.component';
import { PlantFactoryControllerDeleteDialogComponent } from './delete/plant-factory-controller-delete-dialog.component';
import { PlantFactoryControllerRoutingModule } from './route/plant-factory-controller-routing.module';

@NgModule({
  imports: [SharedModule, PlantFactoryControllerRoutingModule],
  declarations: [
    PlantFactoryControllerComponent,
    PlantFactoryControllerDetailComponent,
    PlantFactoryControllerUpdateComponent,
    PlantFactoryControllerDeleteDialogComponent,
  ],
  entryComponents: [PlantFactoryControllerDeleteDialogComponent],
})
export class PlantFactoryControllerModule {}
