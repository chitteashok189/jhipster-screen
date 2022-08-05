import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { NutrientComponent } from './list/nutrient.component';
import { NutrientDetailComponent } from './detail/nutrient-detail.component';
import { NutrientUpdateComponent } from './update/nutrient-update.component';
import { NutrientDeleteDialogComponent } from './delete/nutrient-delete-dialog.component';
import { NutrientRoutingModule } from './route/nutrient-routing.module';

@NgModule({
  imports: [SharedModule, NutrientRoutingModule],
  declarations: [NutrientComponent, NutrientDetailComponent, NutrientUpdateComponent, NutrientDeleteDialogComponent],
  entryComponents: [NutrientDeleteDialogComponent],
})
export class NutrientModule {}
