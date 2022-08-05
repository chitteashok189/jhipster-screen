import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { DiseaseComponent } from './list/disease.component';
import { DiseaseDetailComponent } from './detail/disease-detail.component';
import { DiseaseUpdateComponent } from './update/disease-update.component';
import { DiseaseDeleteDialogComponent } from './delete/disease-delete-dialog.component';
import { DiseaseRoutingModule } from './route/disease-routing.module';

@NgModule({
  imports: [SharedModule, DiseaseRoutingModule],
  declarations: [DiseaseComponent, DiseaseDetailComponent, DiseaseUpdateComponent, DiseaseDeleteDialogComponent],
  entryComponents: [DiseaseDeleteDialogComponent],
})
export class DiseaseModule {}
