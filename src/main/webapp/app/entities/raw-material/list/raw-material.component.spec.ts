import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';

import { RawMaterialService } from '../service/raw-material.service';

import { RawMaterialComponent } from './raw-material.component';

describe('RawMaterial Management Component', () => {
  let comp: RawMaterialComponent;
  let fixture: ComponentFixture<RawMaterialComponent>;
  let service: RawMaterialService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      declarations: [RawMaterialComponent],
    })
      .overrideTemplate(RawMaterialComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(RawMaterialComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(RawMaterialService);

    const headers = new HttpHeaders();
    jest.spyOn(service, 'query').mockReturnValue(
      of(
        new HttpResponse({
          body: [{ id: 123 }],
          headers,
        })
      )
    );
  });

  it('Should call load all on init', () => {
    // WHEN
    comp.ngOnInit();

    // THEN
    expect(service.query).toHaveBeenCalled();
    expect(comp.rawMaterials?.[0]).toEqual(expect.objectContaining({ id: 123 }));
  });
});
