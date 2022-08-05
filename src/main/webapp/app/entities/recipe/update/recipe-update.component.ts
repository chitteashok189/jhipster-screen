import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';

import { IRecipe, Recipe } from '../recipe.model';
import { RecipeService } from '../service/recipe.service';
import { IPlant } from 'app/entities/plant/plant.model';
import { PlantService } from 'app/entities/plant/service/plant.service';
import { Ec } from 'app/entities/enumerations/ec.model';

@Component({
  selector: 'jhi-recipe-update',
  templateUrl: './recipe-update.component.html',
})
export class RecipeUpdateComponent implements OnInit {
  isSaving = false;
  ecValues = Object.keys(Ec);

  plantsSharedCollection: IPlant[] = [];

  editForm = this.fb.group({
    id: [],
    gUID: [],
    plantName: [],
    recipeType: [],
    pHMin: [],
    pHMax: [],
    ecMin: [],
    eCMax: [],
    airTempMax: [],
    airTempMin: [],
    humidityMax: [],
    humidityMin: [],
    nutrientTempMax: [],
    nutrientTempMin: [],
    luxGermMax: [],
    luxGermMin: [],
    lightGermDor: [],
    lightGermCycle: [],
    luxGrowMax: [],
    luxGrowMin: [],
    lightGrowDor: [],
    lightGrowCycle: [],
    co2LightMax: [],
    co2LightMin: [],
    co2DarkMax: [],
    co2DarkMin: [],
    dOMax: [],
    dOMin: [],
    mediaMoistureMax: [],
    mediaMoistureMin: [],
    nitrogen: [],
    phosphorus: [],
    potassium: [],
    sulphur: [],
    calcium: [],
    magnesium: [],
    manganese: [],
    iron: [],
    boron: [],
    copper: [],
    zinc: [],
    molybdenum: [],
    germinationTAT: [],
    identificationComment: [],
    growingComment: [],
    usageComment: [],
    createdBy: [],
    createdOn: [],
    updatedBy: [],
    updatedOn: [],
    plantID: [],
  });

  constructor(
    protected recipeService: RecipeService,
    protected plantService: PlantService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ recipe }) => {
      if (recipe.id === undefined) {
        const today = dayjs().startOf('day');
        recipe.createdOn = today;
        recipe.updatedOn = today;
      }

      this.updateForm(recipe);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const recipe = this.createFromForm();
    if (recipe.id !== undefined) {
      this.subscribeToSaveResponse(this.recipeService.update(recipe));
    } else {
      this.subscribeToSaveResponse(this.recipeService.create(recipe));
    }
  }

  trackPlantById(_index: number, item: IPlant): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IRecipe>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe({
      next: () => this.onSaveSuccess(),
      error: () => this.onSaveError(),
    });
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(recipe: IRecipe): void {
    this.editForm.patchValue({
      id: recipe.id,
      gUID: recipe.gUID,
      plantName: recipe.plantName,
      recipeType: recipe.recipeType,
      pHMin: recipe.pHMin,
      pHMax: recipe.pHMax,
      ecMin: recipe.ecMin,
      eCMax: recipe.eCMax,
      airTempMax: recipe.airTempMax,
      airTempMin: recipe.airTempMin,
      humidityMax: recipe.humidityMax,
      humidityMin: recipe.humidityMin,
      nutrientTempMax: recipe.nutrientTempMax,
      nutrientTempMin: recipe.nutrientTempMin,
      luxGermMax: recipe.luxGermMax,
      luxGermMin: recipe.luxGermMin,
      lightGermDor: recipe.lightGermDor,
      lightGermCycle: recipe.lightGermCycle,
      luxGrowMax: recipe.luxGrowMax,
      luxGrowMin: recipe.luxGrowMin,
      lightGrowDor: recipe.lightGrowDor,
      lightGrowCycle: recipe.lightGrowCycle,
      co2LightMax: recipe.co2LightMax,
      co2LightMin: recipe.co2LightMin,
      co2DarkMax: recipe.co2DarkMax,
      co2DarkMin: recipe.co2DarkMin,
      dOMax: recipe.dOMax,
      dOMin: recipe.dOMin,
      mediaMoistureMax: recipe.mediaMoistureMax,
      mediaMoistureMin: recipe.mediaMoistureMin,
      nitrogen: recipe.nitrogen,
      phosphorus: recipe.phosphorus,
      potassium: recipe.potassium,
      sulphur: recipe.sulphur,
      calcium: recipe.calcium,
      magnesium: recipe.magnesium,
      manganese: recipe.manganese,
      iron: recipe.iron,
      boron: recipe.boron,
      copper: recipe.copper,
      zinc: recipe.zinc,
      molybdenum: recipe.molybdenum,
      germinationTAT: recipe.germinationTAT,
      identificationComment: recipe.identificationComment,
      growingComment: recipe.growingComment,
      usageComment: recipe.usageComment,
      createdBy: recipe.createdBy,
      createdOn: recipe.createdOn ? recipe.createdOn.format(DATE_TIME_FORMAT) : null,
      updatedBy: recipe.updatedBy,
      updatedOn: recipe.updatedOn ? recipe.updatedOn.format(DATE_TIME_FORMAT) : null,
      plantID: recipe.plantID,
    });

    this.plantsSharedCollection = this.plantService.addPlantToCollectionIfMissing(this.plantsSharedCollection, recipe.plantID);
  }

  protected loadRelationshipsOptions(): void {
    this.plantService
      .query()
      .pipe(map((res: HttpResponse<IPlant[]>) => res.body ?? []))
      .pipe(map((plants: IPlant[]) => this.plantService.addPlantToCollectionIfMissing(plants, this.editForm.get('plantID')!.value)))
      .subscribe((plants: IPlant[]) => (this.plantsSharedCollection = plants));
  }

  protected createFromForm(): IRecipe {
    return {
      ...new Recipe(),
      id: this.editForm.get(['id'])!.value,
      gUID: this.editForm.get(['gUID'])!.value,
      plantName: this.editForm.get(['plantName'])!.value,
      recipeType: this.editForm.get(['recipeType'])!.value,
      pHMin: this.editForm.get(['pHMin'])!.value,
      pHMax: this.editForm.get(['pHMax'])!.value,
      ecMin: this.editForm.get(['ecMin'])!.value,
      eCMax: this.editForm.get(['eCMax'])!.value,
      airTempMax: this.editForm.get(['airTempMax'])!.value,
      airTempMin: this.editForm.get(['airTempMin'])!.value,
      humidityMax: this.editForm.get(['humidityMax'])!.value,
      humidityMin: this.editForm.get(['humidityMin'])!.value,
      nutrientTempMax: this.editForm.get(['nutrientTempMax'])!.value,
      nutrientTempMin: this.editForm.get(['nutrientTempMin'])!.value,
      luxGermMax: this.editForm.get(['luxGermMax'])!.value,
      luxGermMin: this.editForm.get(['luxGermMin'])!.value,
      lightGermDor: this.editForm.get(['lightGermDor'])!.value,
      lightGermCycle: this.editForm.get(['lightGermCycle'])!.value,
      luxGrowMax: this.editForm.get(['luxGrowMax'])!.value,
      luxGrowMin: this.editForm.get(['luxGrowMin'])!.value,
      lightGrowDor: this.editForm.get(['lightGrowDor'])!.value,
      lightGrowCycle: this.editForm.get(['lightGrowCycle'])!.value,
      co2LightMax: this.editForm.get(['co2LightMax'])!.value,
      co2LightMin: this.editForm.get(['co2LightMin'])!.value,
      co2DarkMax: this.editForm.get(['co2DarkMax'])!.value,
      co2DarkMin: this.editForm.get(['co2DarkMin'])!.value,
      dOMax: this.editForm.get(['dOMax'])!.value,
      dOMin: this.editForm.get(['dOMin'])!.value,
      mediaMoistureMax: this.editForm.get(['mediaMoistureMax'])!.value,
      mediaMoistureMin: this.editForm.get(['mediaMoistureMin'])!.value,
      nitrogen: this.editForm.get(['nitrogen'])!.value,
      phosphorus: this.editForm.get(['phosphorus'])!.value,
      potassium: this.editForm.get(['potassium'])!.value,
      sulphur: this.editForm.get(['sulphur'])!.value,
      calcium: this.editForm.get(['calcium'])!.value,
      magnesium: this.editForm.get(['magnesium'])!.value,
      manganese: this.editForm.get(['manganese'])!.value,
      iron: this.editForm.get(['iron'])!.value,
      boron: this.editForm.get(['boron'])!.value,
      copper: this.editForm.get(['copper'])!.value,
      zinc: this.editForm.get(['zinc'])!.value,
      molybdenum: this.editForm.get(['molybdenum'])!.value,
      germinationTAT: this.editForm.get(['germinationTAT'])!.value,
      identificationComment: this.editForm.get(['identificationComment'])!.value,
      growingComment: this.editForm.get(['growingComment'])!.value,
      usageComment: this.editForm.get(['usageComment'])!.value,
      createdBy: this.editForm.get(['createdBy'])!.value,
      createdOn: this.editForm.get(['createdOn'])!.value ? dayjs(this.editForm.get(['createdOn'])!.value, DATE_TIME_FORMAT) : undefined,
      updatedBy: this.editForm.get(['updatedBy'])!.value,
      updatedOn: this.editForm.get(['updatedOn'])!.value ? dayjs(this.editForm.get(['updatedOn'])!.value, DATE_TIME_FORMAT) : undefined,
      plantID: this.editForm.get(['plantID'])!.value,
    };
  }
}
