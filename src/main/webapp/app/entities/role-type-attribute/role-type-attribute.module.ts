import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { RoleTypeAttributeComponent } from './list/role-type-attribute.component';
import { RoleTypeAttributeDetailComponent } from './detail/role-type-attribute-detail.component';
import { RoleTypeAttributeUpdateComponent } from './update/role-type-attribute-update.component';
import { RoleTypeAttributeDeleteDialogComponent } from './delete/role-type-attribute-delete-dialog.component';
import { RoleTypeAttributeRoutingModule } from './route/role-type-attribute-routing.module';

@NgModule({
  imports: [SharedModule, RoleTypeAttributeRoutingModule],
  declarations: [
    RoleTypeAttributeComponent,
    RoleTypeAttributeDetailComponent,
    RoleTypeAttributeUpdateComponent,
    RoleTypeAttributeDeleteDialogComponent,
  ],
  entryComponents: [RoleTypeAttributeDeleteDialogComponent],
})
export class RoleTypeAttributeModule {}
