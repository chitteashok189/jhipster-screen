import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { SecurityGroupPermissionComponent } from './list/security-group-permission.component';
import { SecurityGroupPermissionDetailComponent } from './detail/security-group-permission-detail.component';
import { SecurityGroupPermissionUpdateComponent } from './update/security-group-permission-update.component';
import { SecurityGroupPermissionDeleteDialogComponent } from './delete/security-group-permission-delete-dialog.component';
import { SecurityGroupPermissionRoutingModule } from './route/security-group-permission-routing.module';

@NgModule({
  imports: [SharedModule, SecurityGroupPermissionRoutingModule],
  declarations: [
    SecurityGroupPermissionComponent,
    SecurityGroupPermissionDetailComponent,
    SecurityGroupPermissionUpdateComponent,
    SecurityGroupPermissionDeleteDialogComponent,
  ],
  entryComponents: [SecurityGroupPermissionDeleteDialogComponent],
})
export class SecurityGroupPermissionModule {}
