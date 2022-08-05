import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { SeedComponent } from '../list/seed.component';
import { SeedDetailComponent } from '../detail/seed-detail.component';
import { SeedUpdateComponent } from '../update/seed-update.component';
import { SeedRoutingResolveService } from './seed-routing-resolve.service';

const seedRoute: Routes = [
  {
    path: '',
    component: SeedComponent,
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: SeedDetailComponent,
    resolve: {
      seed: SeedRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: SeedUpdateComponent,
    resolve: {
      seed: SeedRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: SeedUpdateComponent,
    resolve: {
      seed: SeedRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(seedRoute)],
  exports: [RouterModule],
})
export class SeedRoutingModule {}
