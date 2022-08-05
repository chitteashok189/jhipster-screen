import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { PestControlComponent } from './list/pest-control.component';
import { PestControlDetailComponent } from './detail/pest-control-detail.component';
import { PestControlUpdateComponent } from './update/pest-control-update.component';
import { PestControlDeleteDialogComponent } from './delete/pest-control-delete-dialog.component';
import { PestControlRoutingModule } from './route/pest-control-routing.module';

@NgModule({
  imports: [SharedModule, PestControlRoutingModule],
  declarations: [PestControlComponent, PestControlDetailComponent, PestControlUpdateComponent, PestControlDeleteDialogComponent],
  entryComponents: [PestControlDeleteDialogComponent],
})
export class PestControlModule {}
