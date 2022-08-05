import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { DisorderComponent } from './list/disorder.component';
import { DisorderDetailComponent } from './detail/disorder-detail.component';
import { DisorderUpdateComponent } from './update/disorder-update.component';
import { DisorderDeleteDialogComponent } from './delete/disorder-delete-dialog.component';
import { DisorderRoutingModule } from './route/disorder-routing.module';

@NgModule({
  imports: [SharedModule, DisorderRoutingModule],
  declarations: [DisorderComponent, DisorderDetailComponent, DisorderUpdateComponent, DisorderDeleteDialogComponent],
  entryComponents: [DisorderDeleteDialogComponent],
})
export class DisorderModule {}
