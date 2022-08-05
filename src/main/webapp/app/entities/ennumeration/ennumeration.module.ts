import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { EnnumerationComponent } from './list/ennumeration.component';
import { EnnumerationDetailComponent } from './detail/ennumeration-detail.component';
import { EnnumerationUpdateComponent } from './update/ennumeration-update.component';
import { EnnumerationDeleteDialogComponent } from './delete/ennumeration-delete-dialog.component';
import { EnnumerationRoutingModule } from './route/ennumeration-routing.module';

@NgModule({
  imports: [SharedModule, EnnumerationRoutingModule],
  declarations: [EnnumerationComponent, EnnumerationDetailComponent, EnnumerationUpdateComponent, EnnumerationDeleteDialogComponent],
  entryComponents: [EnnumerationDeleteDialogComponent],
})
export class EnnumerationModule {}
