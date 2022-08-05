import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { NutrientComponent } from '../list/nutrient.component';
import { NutrientDetailComponent } from '../detail/nutrient-detail.component';
import { NutrientUpdateComponent } from '../update/nutrient-update.component';
import { NutrientRoutingResolveService } from './nutrient-routing-resolve.service';

const nutrientRoute: Routes = [
  {
    path: '',
    component: NutrientComponent,
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: NutrientDetailComponent,
    resolve: {
      nutrient: NutrientRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: NutrientUpdateComponent,
    resolve: {
      nutrient: NutrientRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: NutrientUpdateComponent,
    resolve: {
      nutrient: NutrientRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(nutrientRoute)],
  exports: [RouterModule],
})
export class NutrientRoutingModule {}
