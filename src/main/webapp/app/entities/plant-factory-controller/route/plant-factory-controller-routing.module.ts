import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { PlantFactoryControllerComponent } from '../list/plant-factory-controller.component';
import { PlantFactoryControllerDetailComponent } from '../detail/plant-factory-controller-detail.component';
import { PlantFactoryControllerUpdateComponent } from '../update/plant-factory-controller-update.component';
import { PlantFactoryControllerRoutingResolveService } from './plant-factory-controller-routing-resolve.service';

const plantFactoryControllerRoute: Routes = [
  {
    path: '',
    component: PlantFactoryControllerComponent,
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: PlantFactoryControllerDetailComponent,
    resolve: {
      plantFactoryController: PlantFactoryControllerRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: PlantFactoryControllerUpdateComponent,
    resolve: {
      plantFactoryController: PlantFactoryControllerRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: PlantFactoryControllerUpdateComponent,
    resolve: {
      plantFactoryController: PlantFactoryControllerRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(plantFactoryControllerRoute)],
  exports: [RouterModule],
})
export class PlantFactoryControllerRoutingModule {}
