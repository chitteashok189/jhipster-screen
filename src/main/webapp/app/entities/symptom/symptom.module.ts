import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { SymptomComponent } from './list/symptom.component';
import { SymptomDetailComponent } from './detail/symptom-detail.component';
import { SymptomUpdateComponent } from './update/symptom-update.component';
import { SymptomDeleteDialogComponent } from './delete/symptom-delete-dialog.component';
import { SymptomRoutingModule } from './route/symptom-routing.module';

@NgModule({
  imports: [SharedModule, SymptomRoutingModule],
  declarations: [SymptomComponent, SymptomDetailComponent, SymptomUpdateComponent, SymptomDeleteDialogComponent],
  entryComponents: [SymptomDeleteDialogComponent],
})
export class SymptomModule {}
