import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'party',
        data: { pageTitle: 'blogApp.party.home.title' },
        loadChildren: () => import('./party/party.module').then(m => m.PartyModule),
      },
      {
        path: 'party-group',
        data: { pageTitle: 'blogApp.partyGroup.home.title' },
        loadChildren: () => import('./party-group/party-group.module').then(m => m.PartyGroupModule),
      },
      {
        path: 'party-type',
        data: { pageTitle: 'blogApp.partyType.home.title' },
        loadChildren: () => import('./party-type/party-type.module').then(m => m.PartyTypeModule),
      },
      {
        path: 'party-type-attribute',
        data: { pageTitle: 'blogApp.partyTypeAttribute.home.title' },
        loadChildren: () => import('./party-type-attribute/party-type-attribute.module').then(m => m.PartyTypeAttributeModule),
      },
      {
        path: 'ennumeration',
        data: { pageTitle: 'blogApp.ennumeration.home.title' },
        loadChildren: () => import('./ennumeration/ennumeration.module').then(m => m.EnnumerationModule),
      },
      {
        path: 'ennumeration-type',
        data: { pageTitle: 'blogApp.ennumerationType.home.title' },
        loadChildren: () => import('./ennumeration-type/ennumeration-type.module').then(m => m.EnnumerationTypeModule),
      },
      {
        path: 'party-attribute',
        data: { pageTitle: 'blogApp.partyAttribute.home.title' },
        loadChildren: () => import('./party-attribute/party-attribute.module').then(m => m.PartyAttributeModule),
      },
      {
        path: 'party-classification',
        data: { pageTitle: 'blogApp.partyClassification.home.title' },
        loadChildren: () => import('./party-classification/party-classification.module').then(m => m.PartyClassificationModule),
      },
      {
        path: 'party-role',
        data: { pageTitle: 'blogApp.partyRole.home.title' },
        loadChildren: () => import('./party-role/party-role.module').then(m => m.PartyRoleModule),
      },
      {
        path: 'party-relationship',
        data: { pageTitle: 'blogApp.partyRelationship.home.title' },
        loadChildren: () => import('./party-relationship/party-relationship.module').then(m => m.PartyRelationshipModule),
      },
      {
        path: 'party-relationship-type',
        data: { pageTitle: 'blogApp.partyRelationshipType.home.title' },
        loadChildren: () => import('./party-relationship-type/party-relationship-type.module').then(m => m.PartyRelationshipTypeModule),
      },
      {
        path: 'role-type',
        data: { pageTitle: 'blogApp.roleType.home.title' },
        loadChildren: () => import('./role-type/role-type.module').then(m => m.RoleTypeModule),
      },
      {
        path: 'role-type-attribute',
        data: { pageTitle: 'blogApp.roleTypeAttribute.home.title' },
        loadChildren: () => import('./role-type-attribute/role-type-attribute.module').then(m => m.RoleTypeAttributeModule),
      },
      {
        path: 'party-note',
        data: { pageTitle: 'blogApp.partyNote.home.title' },
        loadChildren: () => import('./party-note/party-note.module').then(m => m.PartyNoteModule),
      },
      {
        path: 'party-status',
        data: { pageTitle: 'blogApp.partyStatus.home.title' },
        loadChildren: () => import('./party-status/party-status.module').then(m => m.PartyStatusModule),
      },
      {
        path: 'party-status-type',
        data: { pageTitle: 'blogApp.partyStatusType.home.title' },
        loadChildren: () => import('./party-status-type/party-status-type.module').then(m => m.PartyStatusTypeModule),
      },
      {
        path: 'party-status-item',
        data: { pageTitle: 'blogApp.partyStatusItem.home.title' },
        loadChildren: () => import('./party-status-item/party-status-item.module').then(m => m.PartyStatusItemModule),
      },
      {
        path: 'person',
        data: { pageTitle: 'blogApp.person.home.title' },
        loadChildren: () => import('./person/person.module').then(m => m.PersonModule),
      },
      {
        path: 'application-user',
        data: { pageTitle: 'blogApp.applicationUser.home.title' },
        loadChildren: () => import('./application-user/application-user.module').then(m => m.ApplicationUserModule),
      },
      {
        path: 'security-group',
        data: { pageTitle: 'blogApp.securityGroup.home.title' },
        loadChildren: () => import('./security-group/security-group.module').then(m => m.SecurityGroupModule),
      },
      {
        path: 'security-permission',
        data: { pageTitle: 'blogApp.securityPermission.home.title' },
        loadChildren: () => import('./security-permission/security-permission.module').then(m => m.SecurityPermissionModule),
      },
      {
        path: 'application-user-security-group',
        data: { pageTitle: 'blogApp.applicationUserSecurityGroup.home.title' },
        loadChildren: () =>
          import('./application-user-security-group/application-user-security-group.module').then(
            m => m.ApplicationUserSecurityGroupModule
          ),
      },
      {
        path: 'security-group-permission',
        data: { pageTitle: 'blogApp.securityGroupPermission.home.title' },
        loadChildren: () =>
          import('./security-group-permission/security-group-permission.module').then(m => m.SecurityGroupPermissionModule),
      },
      {
        path: 'farm',
        data: { pageTitle: 'blogApp.farm.home.title' },
        loadChildren: () => import('./farm/farm.module').then(m => m.FarmModule),
      },
      {
        path: 'plant-factory',
        data: { pageTitle: 'blogApp.plantFactory.home.title' },
        loadChildren: () => import('./plant-factory/plant-factory.module').then(m => m.PlantFactoryModule),
      },
      {
        path: 'zone',
        data: { pageTitle: 'blogApp.zone.home.title' },
        loadChildren: () => import('./zone/zone.module').then(m => m.ZoneModule),
      },
      {
        path: 'grow-bed',
        data: { pageTitle: 'blogApp.growBed.home.title' },
        loadChildren: () => import('./grow-bed/grow-bed.module').then(m => m.GrowBedModule),
      },
      {
        path: 'location',
        data: { pageTitle: 'blogApp.location.home.title' },
        loadChildren: () => import('./location/location.module').then(m => m.LocationModule),
      },
      {
        path: 'device',
        data: { pageTitle: 'blogApp.device.home.title' },
        loadChildren: () => import('./device/device.module').then(m => m.DeviceModule),
      },
      {
        path: 'device-level',
        data: { pageTitle: 'blogApp.deviceLevel.home.title' },
        loadChildren: () => import('./device-level/device-level.module').then(m => m.DeviceLevelModule),
      },
      {
        path: 'device-model',
        data: { pageTitle: 'blogApp.deviceModel.home.title' },
        loadChildren: () => import('./device-model/device-model.module').then(m => m.DeviceModelModule),
      },
      {
        path: 'sensor',
        data: { pageTitle: 'blogApp.sensor.home.title' },
        loadChildren: () => import('./sensor/sensor.module').then(m => m.SensorModule),
      },
      {
        path: 'sensor-model',
        data: { pageTitle: 'blogApp.sensorModel.home.title' },
        loadChildren: () => import('./sensor-model/sensor-model.module').then(m => m.SensorModelModule),
      },
      {
        path: 'plant',
        data: { pageTitle: 'blogApp.plant.home.title' },
        loadChildren: () => import('./plant/plant.module').then(m => m.PlantModule),
      },
      {
        path: 'product',
        data: { pageTitle: 'blogApp.product.home.title' },
        loadChildren: () => import('./product/product.module').then(m => m.ProductModule),
      },
      {
        path: 'seed',
        data: { pageTitle: 'blogApp.seed.home.title' },
        loadChildren: () => import('./seed/seed.module').then(m => m.SeedModule),
      },
      {
        path: 'breeder',
        data: { pageTitle: 'blogApp.breeder.home.title' },
        loadChildren: () => import('./breeder/breeder.module').then(m => m.BreederModule),
      },
      {
        path: 'raw-material',
        data: { pageTitle: 'blogApp.rawMaterial.home.title' },
        loadChildren: () => import('./raw-material/raw-material.module').then(m => m.RawMaterialModule),
      },
      {
        path: 'recipe',
        data: { pageTitle: 'blogApp.recipe.home.title' },
        loadChildren: () => import('./recipe/recipe.module').then(m => m.RecipeModule),
      },
      {
        path: 'season',
        data: { pageTitle: 'blogApp.season.home.title' },
        loadChildren: () => import('./season/season.module').then(m => m.SeasonModule),
      },
      {
        path: 'climate',
        data: { pageTitle: 'blogApp.climate.home.title' },
        loadChildren: () => import('./climate/climate.module').then(m => m.ClimateModule),
      },
      {
        path: 'irrigation',
        data: { pageTitle: 'blogApp.irrigation.home.title' },
        loadChildren: () => import('./irrigation/irrigation.module').then(m => m.IrrigationModule),
      },
      {
        path: 'plant-factory-controller',
        data: { pageTitle: 'blogApp.plantFactoryController.home.title' },
        loadChildren: () => import('./plant-factory-controller/plant-factory-controller.module').then(m => m.PlantFactoryControllerModule),
      },
      {
        path: 'dosing',
        data: { pageTitle: 'blogApp.dosing.home.title' },
        loadChildren: () => import('./dosing/dosing.module').then(m => m.DosingModule),
      },
      {
        path: 'light',
        data: { pageTitle: 'blogApp.light.home.title' },
        loadChildren: () => import('./light/light.module').then(m => m.LightModule),
      },
      {
        path: 'crop',
        data: { pageTitle: 'blogApp.crop.home.title' },
        loadChildren: () => import('./crop/crop.module').then(m => m.CropModule),
      },
      {
        path: 'calendar',
        data: { pageTitle: 'blogApp.calendar.home.title' },
        loadChildren: () => import('./calendar/calendar.module').then(m => m.CalendarModule),
      },
      {
        path: 'weather',
        data: { pageTitle: 'blogApp.weather.home.title' },
        loadChildren: () => import('./weather/weather.module').then(m => m.WeatherModule),
      },
      {
        path: 'scouting',
        data: { pageTitle: 'blogApp.scouting.home.title' },
        loadChildren: () => import('./scouting/scouting.module').then(m => m.ScoutingModule),
      },
      {
        path: 'pest',
        data: { pageTitle: 'blogApp.pest.home.title' },
        loadChildren: () => import('./pest/pest.module').then(m => m.PestModule),
      },
      {
        path: 'pest-control',
        data: { pageTitle: 'blogApp.pestControl.home.title' },
        loadChildren: () => import('./pest-control/pest-control.module').then(m => m.PestControlModule),
      },
      {
        path: 'disease',
        data: { pageTitle: 'blogApp.disease.home.title' },
        loadChildren: () => import('./disease/disease.module').then(m => m.DiseaseModule),
      },
      {
        path: 'disease-control',
        data: { pageTitle: 'blogApp.diseaseControl.home.title' },
        loadChildren: () => import('./disease-control/disease-control.module').then(m => m.DiseaseControlModule),
      },
      {
        path: 'nutrient',
        data: { pageTitle: 'blogApp.nutrient.home.title' },
        loadChildren: () => import('./nutrient/nutrient.module').then(m => m.NutrientModule),
      },
      {
        path: 'activity',
        data: { pageTitle: 'blogApp.activity.home.title' },
        loadChildren: () => import('./activity/activity.module').then(m => m.ActivityModule),
      },
      {
        path: 'symptom',
        data: { pageTitle: 'blogApp.symptom.home.title' },
        loadChildren: () => import('./symptom/symptom.module').then(m => m.SymptomModule),
      },
      {
        path: 'disorder',
        data: { pageTitle: 'blogApp.disorder.home.title' },
        loadChildren: () => import('./disorder/disorder.module').then(m => m.DisorderModule),
      },
      {
        path: 'tool',
        data: { pageTitle: 'blogApp.tool.home.title' },
        loadChildren: () => import('./tool/tool.module').then(m => m.ToolModule),
      },
      {
        path: 'harvest',
        data: { pageTitle: 'blogApp.harvest.home.title' },
        loadChildren: () => import('./harvest/harvest.module').then(m => m.HarvestModule),
      },
      {
        path: 'lot',
        data: { pageTitle: 'blogApp.lot.home.title' },
        loadChildren: () => import('./lot/lot.module').then(m => m.LotModule),
      },
      {
        path: 'inspection',
        data: { pageTitle: 'blogApp.inspection.home.title' },
        loadChildren: () => import('./inspection/inspection.module').then(m => m.InspectionModule),
      },
      {
        path: 'inspection-record',
        data: { pageTitle: 'blogApp.inspectionRecord.home.title' },
        loadChildren: () => import('./inspection-record/inspection-record.module').then(m => m.InspectionRecordModule),
      },
      {
        path: 'alert',
        data: { pageTitle: 'blogApp.alert.home.title' },
        loadChildren: () => import('./alert/alert.module').then(m => m.AlertModule),
      },
      {
        path: 'reminder',
        data: { pageTitle: 'blogApp.reminder.home.title' },
        loadChildren: () => import('./reminder/reminder.module').then(m => m.ReminderModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class EntityRoutingModule {}
