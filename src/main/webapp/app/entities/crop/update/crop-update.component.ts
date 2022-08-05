import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';

import { ICrop, Crop } from '../crop.model';
import { CropService } from '../service/crop.service';
import { IPlant } from 'app/entities/plant/plant.model';
import { PlantService } from 'app/entities/plant/service/plant.service';
import { IPlantFactory } from 'app/entities/plant-factory/plant-factory.model';
import { PlantFactoryService } from 'app/entities/plant-factory/service/plant-factory.service';
import { ITool } from 'app/entities/tool/tool.model';
import { ToolService } from 'app/entities/tool/service/tool.service';
import { ISeason } from 'app/entities/season/season.model';
import { SeasonService } from 'app/entities/season/service/season.service';
import { IProduct } from 'app/entities/product/product.model';
import { ProductService } from 'app/entities/product/service/product.service';
import { CropTyp } from 'app/entities/enumerations/crop-typ.model';
import { Horti } from 'app/entities/enumerations/horti.model';
import { GroPhase } from 'app/entities/enumerations/gro-phase.model';
import { PlantingMat } from 'app/entities/enumerations/planting-mat.model';

@Component({
  selector: 'jhi-crop-update',
  templateUrl: './crop-update.component.html',
})
export class CropUpdateComponent implements OnInit {
  isSaving = false;
  cropTypValues = Object.keys(CropTyp);
  hortiValues = Object.keys(Horti);
  groPhaseValues = Object.keys(GroPhase);
  plantingMatValues = Object.keys(PlantingMat);

  plantsSharedCollection: IPlant[] = [];
  plantFactoriesSharedCollection: IPlantFactory[] = [];
  toolsSharedCollection: ITool[] = [];
  seasonsSharedCollection: ISeason[] = [];
  productsSharedCollection: IProduct[] = [];

  editForm = this.fb.group({
    id: [],
    gUID: [],
    cropCode: [],
    cropName: [],
    cropType: [],
    horticultureType: [],
    isHybrid: [],
    cultivar: [],
    sowingDate: [],
    sowingDepth: [],
    rowSpacingMax: [],
    rowSpacingMin: [],
    seedDepthMax: [],
    seedDepthMin: [],
    seedSpacingMax: [],
    seedSpacingMin: [],
    yearlyCrops: [],
    growingSeason: [],
    growingPhase: [],
    plantingDate: [],
    plantSpacing: [],
    plantingMaterial: [],
    transplantationDate: [],
    fertigationscheduleID: [],
    plannedYield: [],
    actualYield: [],
    yieldUnit: [],
    createdBy: [],
    createdOn: [],
    updatedBy: [],
    updatedOn: [],
    plantID: [],
    plantFactoryID: [],
    toolID: [],
    seasonID: [],
    productID: [],
  });

  constructor(
    protected cropService: CropService,
    protected plantService: PlantService,
    protected plantFactoryService: PlantFactoryService,
    protected toolService: ToolService,
    protected seasonService: SeasonService,
    protected productService: ProductService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ crop }) => {
      if (crop.id === undefined) {
        const today = dayjs().startOf('day');
        crop.sowingDate = today;
        crop.plantingDate = today;
        crop.transplantationDate = today;
        crop.createdOn = today;
        crop.updatedOn = today;
      }

      this.updateForm(crop);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const crop = this.createFromForm();
    if (crop.id !== undefined) {
      this.subscribeToSaveResponse(this.cropService.update(crop));
    } else {
      this.subscribeToSaveResponse(this.cropService.create(crop));
    }
  }

  trackPlantById(_index: number, item: IPlant): number {
    return item.id!;
  }

  trackPlantFactoryById(_index: number, item: IPlantFactory): number {
    return item.id!;
  }

  trackToolById(_index: number, item: ITool): number {
    return item.id!;
  }

  trackSeasonById(_index: number, item: ISeason): number {
    return item.id!;
  }

  trackProductById(_index: number, item: IProduct): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICrop>>): void {
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

  protected updateForm(crop: ICrop): void {
    this.editForm.patchValue({
      id: crop.id,
      gUID: crop.gUID,
      cropCode: crop.cropCode,
      cropName: crop.cropName,
      cropType: crop.cropType,
      horticultureType: crop.horticultureType,
      isHybrid: crop.isHybrid,
      cultivar: crop.cultivar,
      sowingDate: crop.sowingDate ? crop.sowingDate.format(DATE_TIME_FORMAT) : null,
      sowingDepth: crop.sowingDepth,
      rowSpacingMax: crop.rowSpacingMax,
      rowSpacingMin: crop.rowSpacingMin,
      seedDepthMax: crop.seedDepthMax,
      seedDepthMin: crop.seedDepthMin,
      seedSpacingMax: crop.seedSpacingMax,
      seedSpacingMin: crop.seedSpacingMin,
      yearlyCrops: crop.yearlyCrops,
      growingSeason: crop.growingSeason,
      growingPhase: crop.growingPhase,
      plantingDate: crop.plantingDate ? crop.plantingDate.format(DATE_TIME_FORMAT) : null,
      plantSpacing: crop.plantSpacing,
      plantingMaterial: crop.plantingMaterial,
      transplantationDate: crop.transplantationDate ? crop.transplantationDate.format(DATE_TIME_FORMAT) : null,
      fertigationscheduleID: crop.fertigationscheduleID,
      plannedYield: crop.plannedYield,
      actualYield: crop.actualYield,
      yieldUnit: crop.yieldUnit,
      createdBy: crop.createdBy,
      createdOn: crop.createdOn ? crop.createdOn.format(DATE_TIME_FORMAT) : null,
      updatedBy: crop.updatedBy,
      updatedOn: crop.updatedOn ? crop.updatedOn.format(DATE_TIME_FORMAT) : null,
      plantID: crop.plantID,
      plantFactoryID: crop.plantFactoryID,
      toolID: crop.toolID,
      seasonID: crop.seasonID,
      productID: crop.productID,
    });

    this.plantsSharedCollection = this.plantService.addPlantToCollectionIfMissing(this.plantsSharedCollection, crop.plantID);
    this.plantFactoriesSharedCollection = this.plantFactoryService.addPlantFactoryToCollectionIfMissing(
      this.plantFactoriesSharedCollection,
      crop.plantFactoryID
    );
    this.toolsSharedCollection = this.toolService.addToolToCollectionIfMissing(this.toolsSharedCollection, crop.toolID);
    this.seasonsSharedCollection = this.seasonService.addSeasonToCollectionIfMissing(this.seasonsSharedCollection, crop.seasonID);
    this.productsSharedCollection = this.productService.addProductToCollectionIfMissing(this.productsSharedCollection, crop.productID);
  }

  protected loadRelationshipsOptions(): void {
    this.plantService
      .query()
      .pipe(map((res: HttpResponse<IPlant[]>) => res.body ?? []))
      .pipe(map((plants: IPlant[]) => this.plantService.addPlantToCollectionIfMissing(plants, this.editForm.get('plantID')!.value)))
      .subscribe((plants: IPlant[]) => (this.plantsSharedCollection = plants));

    this.plantFactoryService
      .query()
      .pipe(map((res: HttpResponse<IPlantFactory[]>) => res.body ?? []))
      .pipe(
        map((plantFactories: IPlantFactory[]) =>
          this.plantFactoryService.addPlantFactoryToCollectionIfMissing(plantFactories, this.editForm.get('plantFactoryID')!.value)
        )
      )
      .subscribe((plantFactories: IPlantFactory[]) => (this.plantFactoriesSharedCollection = plantFactories));

    this.toolService
      .query()
      .pipe(map((res: HttpResponse<ITool[]>) => res.body ?? []))
      .pipe(map((tools: ITool[]) => this.toolService.addToolToCollectionIfMissing(tools, this.editForm.get('toolID')!.value)))
      .subscribe((tools: ITool[]) => (this.toolsSharedCollection = tools));

    this.seasonService
      .query()
      .pipe(map((res: HttpResponse<ISeason[]>) => res.body ?? []))
      .pipe(map((seasons: ISeason[]) => this.seasonService.addSeasonToCollectionIfMissing(seasons, this.editForm.get('seasonID')!.value)))
      .subscribe((seasons: ISeason[]) => (this.seasonsSharedCollection = seasons));

    this.productService
      .query()
      .pipe(map((res: HttpResponse<IProduct[]>) => res.body ?? []))
      .pipe(
        map((products: IProduct[]) => this.productService.addProductToCollectionIfMissing(products, this.editForm.get('productID')!.value))
      )
      .subscribe((products: IProduct[]) => (this.productsSharedCollection = products));
  }

  protected createFromForm(): ICrop {
    return {
      ...new Crop(),
      id: this.editForm.get(['id'])!.value,
      gUID: this.editForm.get(['gUID'])!.value,
      cropCode: this.editForm.get(['cropCode'])!.value,
      cropName: this.editForm.get(['cropName'])!.value,
      cropType: this.editForm.get(['cropType'])!.value,
      horticultureType: this.editForm.get(['horticultureType'])!.value,
      isHybrid: this.editForm.get(['isHybrid'])!.value,
      cultivar: this.editForm.get(['cultivar'])!.value,
      sowingDate: this.editForm.get(['sowingDate'])!.value ? dayjs(this.editForm.get(['sowingDate'])!.value, DATE_TIME_FORMAT) : undefined,
      sowingDepth: this.editForm.get(['sowingDepth'])!.value,
      rowSpacingMax: this.editForm.get(['rowSpacingMax'])!.value,
      rowSpacingMin: this.editForm.get(['rowSpacingMin'])!.value,
      seedDepthMax: this.editForm.get(['seedDepthMax'])!.value,
      seedDepthMin: this.editForm.get(['seedDepthMin'])!.value,
      seedSpacingMax: this.editForm.get(['seedSpacingMax'])!.value,
      seedSpacingMin: this.editForm.get(['seedSpacingMin'])!.value,
      yearlyCrops: this.editForm.get(['yearlyCrops'])!.value,
      growingSeason: this.editForm.get(['growingSeason'])!.value,
      growingPhase: this.editForm.get(['growingPhase'])!.value,
      plantingDate: this.editForm.get(['plantingDate'])!.value
        ? dayjs(this.editForm.get(['plantingDate'])!.value, DATE_TIME_FORMAT)
        : undefined,
      plantSpacing: this.editForm.get(['plantSpacing'])!.value,
      plantingMaterial: this.editForm.get(['plantingMaterial'])!.value,
      transplantationDate: this.editForm.get(['transplantationDate'])!.value
        ? dayjs(this.editForm.get(['transplantationDate'])!.value, DATE_TIME_FORMAT)
        : undefined,
      fertigationscheduleID: this.editForm.get(['fertigationscheduleID'])!.value,
      plannedYield: this.editForm.get(['plannedYield'])!.value,
      actualYield: this.editForm.get(['actualYield'])!.value,
      yieldUnit: this.editForm.get(['yieldUnit'])!.value,
      createdBy: this.editForm.get(['createdBy'])!.value,
      createdOn: this.editForm.get(['createdOn'])!.value ? dayjs(this.editForm.get(['createdOn'])!.value, DATE_TIME_FORMAT) : undefined,
      updatedBy: this.editForm.get(['updatedBy'])!.value,
      updatedOn: this.editForm.get(['updatedOn'])!.value ? dayjs(this.editForm.get(['updatedOn'])!.value, DATE_TIME_FORMAT) : undefined,
      plantID: this.editForm.get(['plantID'])!.value,
      plantFactoryID: this.editForm.get(['plantFactoryID'])!.value,
      toolID: this.editForm.get(['toolID'])!.value,
      seasonID: this.editForm.get(['seasonID'])!.value,
      productID: this.editForm.get(['productID'])!.value,
    };
  }
}
