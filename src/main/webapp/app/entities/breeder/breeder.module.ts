import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { BreederComponent } from './list/breeder.component';
import { BreederDetailComponent } from './detail/breeder-detail.component';
import { BreederUpdateComponent } from './update/breeder-update.component';
import { BreederDeleteDialogComponent } from './delete/breeder-delete-dialog.component';
import { BreederRoutingModule } from './route/breeder-routing.module';

@NgModule({
  imports: [SharedModule, BreederRoutingModule],
  declarations: [BreederComponent, BreederDetailComponent, BreederUpdateComponent, BreederDeleteDialogComponent],
  entryComponents: [BreederDeleteDialogComponent],
})
export class BreederModule {}
