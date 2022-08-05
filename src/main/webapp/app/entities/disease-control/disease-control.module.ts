import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { DiseaseControlComponent } from './list/disease-control.component';
import { DiseaseControlDetailComponent } from './detail/disease-control-detail.component';
import { DiseaseControlUpdateComponent } from './update/disease-control-update.component';
import { DiseaseControlDeleteDialogComponent } from './delete/disease-control-delete-dialog.component';
import { DiseaseControlRoutingModule } from './route/disease-control-routing.module';

@NgModule({
  imports: [SharedModule, DiseaseControlRoutingModule],
  declarations: [
    DiseaseControlComponent,
    DiseaseControlDetailComponent,
    DiseaseControlUpdateComponent,
    DiseaseControlDeleteDialogComponent,
  ],
  entryComponents: [DiseaseControlDeleteDialogComponent],
})
export class DiseaseControlModule {}
