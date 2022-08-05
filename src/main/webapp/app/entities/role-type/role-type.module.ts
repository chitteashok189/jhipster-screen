import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { RoleTypeComponent } from './list/role-type.component';
import { RoleTypeDetailComponent } from './detail/role-type-detail.component';
import { RoleTypeUpdateComponent } from './update/role-type-update.component';
import { RoleTypeDeleteDialogComponent } from './delete/role-type-delete-dialog.component';
import { RoleTypeRoutingModule } from './route/role-type-routing.module';

@NgModule({
  imports: [SharedModule, RoleTypeRoutingModule],
  declarations: [RoleTypeComponent, RoleTypeDetailComponent, RoleTypeUpdateComponent, RoleTypeDeleteDialogComponent],
  entryComponents: [RoleTypeDeleteDialogComponent],
})
export class RoleTypeModule {}
