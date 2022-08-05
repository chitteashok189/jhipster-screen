import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { InspectionRecordComponent } from './list/inspection-record.component';
import { InspectionRecordDetailComponent } from './detail/inspection-record-detail.component';
import { InspectionRecordUpdateComponent } from './update/inspection-record-update.component';
import { InspectionRecordDeleteDialogComponent } from './delete/inspection-record-delete-dialog.component';
import { InspectionRecordRoutingModule } from './route/inspection-record-routing.module';

@NgModule({
  imports: [SharedModule, InspectionRecordRoutingModule],
  declarations: [
    InspectionRecordComponent,
    InspectionRecordDetailComponent,
    InspectionRecordUpdateComponent,
    InspectionRecordDeleteDialogComponent,
  ],
  entryComponents: [InspectionRecordDeleteDialogComponent],
})
export class InspectionRecordModule {}
