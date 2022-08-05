import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';

import { IProduct, Product } from '../product.model';
import { ProductService } from '../service/product.service';
import { ProType } from 'app/entities/enumerations/pro-type.model';

@Component({
  selector: 'jhi-product-update',
  templateUrl: './product-update.component.html',
})
export class ProductUpdateComponent implements OnInit {
  isSaving = false;
  proTypeValues = Object.keys(ProType);

  editForm = this.fb.group({
    id: [],
    gUID: [],
    productCode: [],
    productName: [],
    productPrice: [],
    productType: [],
    uOM: [],
    otherProductDetails: [],
    previousEntry: [],
    manufacturer: [],
    productDescription: [],
    imageFileName: [],
    productEntryName: [],
    capacity: [],
    length: [],
    width: [],
    height: [],
    createdBy: [],
    createdOn: [],
    updatedBy: [],
    updatedOn: [],
  });

  constructor(protected productService: ProductService, protected activatedRoute: ActivatedRoute, protected fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ product }) => {
      if (product.id === undefined) {
        const today = dayjs().startOf('day');
        product.createdOn = today;
        product.updatedOn = today;
      }

      this.updateForm(product);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const product = this.createFromForm();
    if (product.id !== undefined) {
      this.subscribeToSaveResponse(this.productService.update(product));
    } else {
      this.subscribeToSaveResponse(this.productService.create(product));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IProduct>>): void {
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

  protected updateForm(product: IProduct): void {
    this.editForm.patchValue({
      id: product.id,
      gUID: product.gUID,
      productCode: product.productCode,
      productName: product.productName,
      productPrice: product.productPrice,
      productType: product.productType,
      uOM: product.uOM,
      otherProductDetails: product.otherProductDetails,
      previousEntry: product.previousEntry,
      manufacturer: product.manufacturer,
      productDescription: product.productDescription,
      imageFileName: product.imageFileName,
      productEntryName: product.productEntryName,
      capacity: product.capacity,
      length: product.length,
      width: product.width,
      height: product.height,
      createdBy: product.createdBy,
      createdOn: product.createdOn ? product.createdOn.format(DATE_TIME_FORMAT) : null,
      updatedBy: product.updatedBy,
      updatedOn: product.updatedOn ? product.updatedOn.format(DATE_TIME_FORMAT) : null,
    });
  }

  protected createFromForm(): IProduct {
    return {
      ...new Product(),
      id: this.editForm.get(['id'])!.value,
      gUID: this.editForm.get(['gUID'])!.value,
      productCode: this.editForm.get(['productCode'])!.value,
      productName: this.editForm.get(['productName'])!.value,
      productPrice: this.editForm.get(['productPrice'])!.value,
      productType: this.editForm.get(['productType'])!.value,
      uOM: this.editForm.get(['uOM'])!.value,
      otherProductDetails: this.editForm.get(['otherProductDetails'])!.value,
      previousEntry: this.editForm.get(['previousEntry'])!.value,
      manufacturer: this.editForm.get(['manufacturer'])!.value,
      productDescription: this.editForm.get(['productDescription'])!.value,
      imageFileName: this.editForm.get(['imageFileName'])!.value,
      productEntryName: this.editForm.get(['productEntryName'])!.value,
      capacity: this.editForm.get(['capacity'])!.value,
      length: this.editForm.get(['length'])!.value,
      width: this.editForm.get(['width'])!.value,
      height: this.editForm.get(['height'])!.value,
      createdBy: this.editForm.get(['createdBy'])!.value,
      createdOn: this.editForm.get(['createdOn'])!.value ? dayjs(this.editForm.get(['createdOn'])!.value, DATE_TIME_FORMAT) : undefined,
      updatedBy: this.editForm.get(['updatedBy'])!.value,
      updatedOn: this.editForm.get(['updatedOn'])!.value ? dayjs(this.editForm.get(['updatedOn'])!.value, DATE_TIME_FORMAT) : undefined,
    };
  }
}
