import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { DosingComponent } from './list/dosing.component';
import { DosingDetailComponent } from './detail/dosing-detail.component';
import { DosingUpdateComponent } from './update/dosing-update.component';
import { DosingDeleteDialogComponent } from './delete/dosing-delete-dialog.component';
import { DosingRoutingModule } from './route/dosing-routing.module';

@NgModule({
  imports: [SharedModule, DosingRoutingModule],
  declarations: [DosingComponent, DosingDetailComponent, DosingUpdateComponent, DosingDeleteDialogComponent],
  entryComponents: [DosingDeleteDialogComponent],
})
export class DosingModule {}
