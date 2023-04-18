export interface Page<ContentType>{
    content: ContentType[],
    pageable: Pageable
    last : boolean,
    totalPages : number,
    totalElements : number,
    size : number,
    number : number,
    first : boolean,
    numberOfElements : number,
    empty : boolean
}

export interface Pageable{
    offset : number,
    pageNumber : number,
    pageSize : number,
    unpaged : boolean,
    paged : boolean
}