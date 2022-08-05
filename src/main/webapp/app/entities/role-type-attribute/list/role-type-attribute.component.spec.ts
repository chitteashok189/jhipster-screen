import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';

import { RoleTypeAttributeService } from '../service/role-type-attribute.service';

import { RoleTypeAttributeComponent } from './role-type-attribute.component';

describe('RoleTypeAttribute Management Component', () => {
  let comp: RoleTypeAttributeComponent;
  let fixture: ComponentFixture<RoleTypeAttributeComponent>;
  let service: RoleTypeAttributeService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      declarations: [RoleTypeAttributeComponent],
    })
      .overrideTemplate(RoleTypeAttributeComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(RoleTypeAttributeComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(RoleTypeAttributeService);

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
    expect(comp.roleTypeAttributes?.[0]).toEqual(expect.objectContaining({ id: 123 }));
  });
});
