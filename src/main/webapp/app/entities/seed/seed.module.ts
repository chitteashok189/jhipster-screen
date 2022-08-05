import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { SeedComponent } from './list/seed.component';
import { SeedDetailComponent } from './detail/seed-detail.component';
import { SeedUpdateComponent } from './update/seed-update.component';
import { SeedDeleteDialogComponent } from './delete/seed-delete-dialog.component';
import { SeedRoutingModule } from './route/seed-routing.module';

@NgModule({
  imports: [SharedModule, SeedRoutingModule],
  declarations: [SeedComponent, SeedDetailComponent, SeedUpdateComponent, SeedDeleteDialogComponent],
  entryComponents: [SeedDeleteDialogComponent],
})
export class SeedModule {}
