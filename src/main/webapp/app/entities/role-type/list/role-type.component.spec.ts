import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';

import { RoleTypeService } from '../service/role-type.service';

import { RoleTypeComponent } from './role-type.component';

describe('RoleType Management Component', () => {
  let comp: RoleTypeComponent;
  let fixture: ComponentFixture<RoleTypeComponent>;
  let service: RoleTypeService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      declarations: [RoleTypeComponent],
    })
      .overrideTemplate(RoleTypeComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(RoleTypeComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(RoleTypeService);

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
    expect(comp.roleTypes?.[0]).toEqual(expect.objectContaining({ id: 123 }));
  });
});
