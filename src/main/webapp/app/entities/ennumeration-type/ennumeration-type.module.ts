import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { EnnumerationTypeComponent } from './list/ennumeration-type.component';
import { EnnumerationTypeDetailComponent } from './detail/ennumeration-type-detail.component';
import { EnnumerationTypeUpdateComponent } from './update/ennumeration-type-update.component';
import { EnnumerationTypeDeleteDialogComponent } from './delete/ennumeration-type-delete-dialog.component';
import { EnnumerationTypeRoutingModule } from './route/ennumeration-type-routing.module';

@NgModule({
  imports: [SharedModule, EnnumerationTypeRoutingModule],
  declarations: [
    EnnumerationTypeComponent,
    EnnumerationTypeDetailComponent,
    EnnumerationTypeUpdateComponent,
    EnnumerationTypeDeleteDialogComponent,
  ],
  entryComponents: [EnnumerationTypeDeleteDialogComponent],
})
export class EnnumerationTypeModule {}
