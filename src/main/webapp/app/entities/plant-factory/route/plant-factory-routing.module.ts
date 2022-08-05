import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { PlantFactoryComponent } from '../list/plant-factory.component';
import { PlantFactoryDetailComponent } from '../detail/plant-factory-detail.component';
import { PlantFactoryUpdateComponent } from '../update/plant-factory-update.component';
import { PlantFactoryRoutingResolveService } from './plant-factory-routing-resolve.service';

const plantFactoryRoute: Routes = [
  {
    path: '',
    component: PlantFactoryComponent,
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: PlantFactoryDetailComponent,
    resolve: {
      plantFactory: PlantFactoryRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: PlantFactoryUpdateComponent,
    resolve: {
      plantFactory: PlantFactoryRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: PlantFactoryUpdateComponent,
    resolve: {
      plantFactory: PlantFactoryRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(plantFactoryRoute)],
  exports: [RouterModule],
})
export class PlantFactoryRoutingModule {}
