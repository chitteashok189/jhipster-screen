import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { PlantFactoryComponent } from './list/plant-factory.component';
import { PlantFactoryDetailComponent } from './detail/plant-factory-detail.component';
import { PlantFactoryUpdateComponent } from './update/plant-factory-update.component';
import { PlantFactoryDeleteDialogComponent } from './delete/plant-factory-delete-dialog.component';
import { PlantFactoryRoutingModule } from './route/plant-factory-routing.module';

@NgModule({
  imports: [SharedModule, PlantFactoryRoutingModule],
  declarations: [PlantFactoryComponent, PlantFactoryDetailComponent, PlantFactoryUpdateComponent, PlantFactoryDeleteDialogComponent],
  entryComponents: [PlantFactoryDeleteDialogComponent],
})
export class PlantFactoryModule {}
