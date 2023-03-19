package com.springboot.fives.paging;

import com.springboot.fives.list.vo.PageInfoVO;


public class PageNavigation {

		public String getPageLinks(PageInfoVO pageInfoVO) {
			StringBuffer sbRet = new StringBuffer();
			WNAnchor wnanchor = getPageAnchor(pageInfoVO);

			//맨처음 페이지
			if(wnanchor.getFirstPage() != -1) {
				sbRet.append("<li class=\"page-item\">");
				sbRet.append("<a class=\"page-link\" href=\"#none\" onClick=\"javascript:doPaging('"+wnanchor.getFirstPage()+"');\" >");
				sbRet.append("<span aria-hidden=\"true\">맨처음</span>");
				sbRet.append("<span class=\"sr-only\">맨처음</span>");
				sbRet.append("</a>");
				sbRet.append("</li>");			
			} else {
				sbRet.append("<li class=\"page-item disabled\">");
				sbRet.append("<a tabindex=\"-1\" class=\"page-link\" href=\"javascript:alert('disabled')\" >");
				sbRet.append("맨처음");
				sbRet.append("</a>");
				sbRet.append("</li>");
			} 			

			//이전 페이지
			if(wnanchor.getFirstPage() != -1) {
				sbRet.append("<li class=\"page-item\">");
				sbRet.append("<a class=\"page-link\" href=\"#none\" onClick=\"javascript:doPaging('"+wnanchor.getBundleBefore()+"');\" >");
				sbRet.append("<span aria-hidden=\"true\">이전페이지</span>");
				sbRet.append("<span class=\"sr-only\">이전페이지</span>");
				sbRet.append("</a>");
				sbRet.append("</li>");					
			} else {
				sbRet.append("<li class=\"page-item disabled\">");
				sbRet.append("<a tabindex=\"-1\" class=\"page-link\" href=\"javascript:alert('disabled')\" >");
				sbRet.append("이전페이지");
				sbRet.append("</a>");
				sbRet.append("</li>");
			} 
 
			int pageCount = wnanchor.getPageCount();
			String pages[][] = wnanchor.getPages();

			for(int i=0; i<pageCount && i < pages.length; i++) {
				if(pages[i][1].equals("-1")) {

					sbRet.append("<li class=\"page-item active\">");
					sbRet.append("<a class=\"page-link\" href=\"#\">"+pages[i][0]+"<span class=\"sr-only\">(current)</span></a>");
					sbRet.append("</li>");
				} else {
					sbRet.append("<li class=\"page-item\">");					
					sbRet.append("<a class=\"page-link\" href=\"javascript:doPaging('"+pages[i][1]+"');\" > "+pages[i][0]+" </a>");
					sbRet.append("</li>");					
				}
			}

			//다음 페이지
			if(wnanchor.getBundleNext() != -1) {
				sbRet.append("<li class=\"page-item\">");
				sbRet.append("<a class=\"page-link\" href=\"#none\" onClick=\"javascript:doPaging('"+wnanchor.getBundleNext()+"');\" >");
				sbRet.append("<span aria-hidden=\"true\">다음페이지</span>");
				sbRet.append("<span class=\"sr-only\">다음페이지</span>");
				sbRet.append("</a>");
				sbRet.append("</li>");	
			} else {
				sbRet.append("<li class=\"page-item disabled\">");
				sbRet.append("<a tabindex=\"-1\" class=\"page-link\" href=\"javascript:alert('disabled')\" >");
				sbRet.append("다음페이지");
				sbRet.append("</a>");
				sbRet.append("</li>");
			}


			//맨끝 페이지
			if(wnanchor.getLastPage() != -1) {
				sbRet.append("<li class=\"page-item\">");
				sbRet.append("<a class=\"page-link\" href=\"#none\" onClick=\"javascript:doPaging('"+wnanchor.getLastPage()+"');\" >");
				sbRet.append("<span aria-hidden=\"true\">맨끝페이지</span>");
				sbRet.append("<span class=\"sr-only\">맨끝페이지</span>");
				sbRet.append("</a>");
				sbRet.append("</li>");	
			} else {
				sbRet.append("<li class=\"page-item disabled\">");
				sbRet.append("<a tabindex=\"-1\" class=\"page-link\" href=\"javascript:alert('disabled')\" >");
				sbRet.append("맨끝페이지");
				sbRet.append("</a>");
				sbRet.append("</li>");	

			}
 
			return sbRet.toString();
		}

		/**
		 * 페이지 Anchor를 생성한다.
		 * @param startNum 검색 결과 시작 offset
		 * @param totalcount 검색 결과의 총 개수
		 * @param resultCnt 검색결과로 요청된 offset의 개수
		 * @param anchorSacle 페이지 이동할 개수
		 * @return WNAnchor Object를 반환
		 */
		public WNAnchor getPageAnchor(PageInfoVO pageInfoVO) {
			WNAnchor anchor = new WNAnchor();

			int totalcount = pageInfoVO.getTotalCount();
			int startNum =	pageInfoVO.getStartCount();
			int resultCnt =	pageInfoVO.getPageViewCount();
			int anchorSacle = pageInfoVO.getBundleCount();

			if(totalcount == 0) {   //등록된 정보가 없으면 페이지 Anchor를 생성 하지 않는다.
				return anchor;
			}

			int curBunbleNum = startNum / (resultCnt * anchorSacle);
			int totalPageCnt = totalcount / resultCnt;
			if(totalcount % resultCnt > 0) {
				totalPageCnt++;
			}

			anchor.setTotalPgCount(totalPageCnt);      // 전체 페이지 세팅

			if ( startNum > 0){        // 이전 페이지
				int beforePg = startNum - resultCnt;
				anchor.setBefore(beforePg);
			}

			if( startNum+resultCnt < totalcount ){        // 다음페이지
				int nextPg = startNum + resultCnt;
				anchor.setNext(nextPg);
			}

			//번들 뒤로 이동
			int bunbleBeforePg = (curBunbleNum-1) * resultCnt * anchorSacle;    //이전 번들로 이동하는 번호
			if(curBunbleNum > 0){
				anchor.setBundleBefore(bunbleBeforePg);
			}

			//번들 앞으로 이동
			int bundleNextPg = (1 + curBunbleNum) * anchorSacle * resultCnt;
			if( totalPageCnt >= anchorSacle && (curBunbleNum+1) * anchorSacle <  totalPageCnt ){
				anchor.setBundleNext(bundleNextPg);
			}

			//맨처음..
			if(startNum != 0 && curBunbleNum != 0){
				anchor.setFirstPage(0);
			}
			//맨끝...
			int lastPage = (resultCnt * totalPageCnt) - resultCnt ;
			if( totalPageCnt >= anchorSacle && (curBunbleNum+1) * anchorSacle <  totalPageCnt ) {
				anchor.setLastPage(lastPage);
			}

			int pageCount = 1;
			String[][] pages = new String[anchorSacle][2];
			for(int i = 0; i < anchorSacle; i++) {
				int startCnt = ((curBunbleNum * anchorSacle) + i) * resultCnt;
				int pageNum = (curBunbleNum * anchorSacle) + i + 1;
				if(startCnt < totalcount) {
					if (startCnt != startNum) {
						pages[i][0] = Integer.toString(pageNum);
						pages[i][1] = Integer.toString(startCnt);
					} else {
						pages[i][0] = Integer.toString(pageNum);
						pages[i][1] = "-1";
						anchor.setCurPageNumber(pageNum);
					}
					anchor.setPageCount(pageCount);
					pageCount++;
				}
			}
			anchor.setPages(pages);
			return anchor;
		}



        /**
         * 검색 결과 페이징 번호를 출력한다.
         * @param startCount 검색 결과 시작 offset
         * @param totalCount 검색 결과의 총 개수
         * @param viewListCount 검색결과로 요청된 offset의 개수
         * @param bundleCount 페이지 이동할 개수
         * @return 페이징 문자열 반환
         */
        public String getPageLinksWebAccess(String base, String url, PageInfoVO pageInfoVO) {

			String RequestURI = base;
			String RequestURL = url;

            StringBuffer sbRet = new StringBuffer();
            WNAnchor wnanchor = getPageAnchor(pageInfoVO);
            String ppreImg="";
            String preImg="";
            String nextImg="";
            String nnextImg="";
            ppreImg = "<IMG SRC='images/navi/icon_first.gif' BORDER='0' align='absmiddle'>";
            preImg = "<IMG SRC='images/navi/icon_preview.gif' BORDER='0' align='absmiddle'>";
            nextImg = "<IMG SRC='images/navi/icon_next.gif' BORDER='0' align='absmiddle'>";
            nnextImg = "<IMG SRC='images/navi/icon_end.gif' BORDER='0' align='absmiddle'>";

            if(wnanchor.getFirstPage() != -1) {
                sbRet.append("<A HREF='" + replaceURL(RequestURI, RequestURL, "startCount",String.valueOf(wnanchor.getFirstPage())) +"'  >"+ppreImg+"</A>&nbsp;&nbsp;");
            } else {
                sbRet.append(ppreImg+"&nbsp;");
            }
            if(wnanchor.getBundleBefore() != -1) {
                sbRet.append("<A HREF='" + replaceURL(RequestURI, RequestURL, "startCount", String.valueOf(wnanchor.getFirstPage())) +"'  >"+preImg+"</A>&nbsp;&nbsp;");
            } else {
                sbRet.append(preImg+"&nbsp;");
            }

            int pageCount = wnanchor.getPageCount();
            String pages[][] = wnanchor.getPages();

            for(int i=0; i<pageCount && i < pages.length; i++) {
                if(pages[i][1].equals("-1")) {
                    sbRet.append("<b>" + pages[i][0]+"</b>");
                } else {
                    sbRet.append("<A HREF='" + replaceURL(RequestURI,RequestURL,"startCount", String.valueOf(pages[i][1])) + "' class='nav'> "+pages[i][0]+" </A>");
                }
                if(pageCount > i+1) {
                    sbRet.append("&nbsp;|&nbsp;");// 페이지 경계 1 | 2 | 3
                }
            }
            if(wnanchor.getBundleNext() != -1) {
                sbRet.append("&nbsp;&nbsp;<A HREF='" + replaceURL(RequestURI,RequestURL,"startCount",String.valueOf(wnanchor.getBundleNext())) + "'>"+nextImg+"</A>");
            } else {
                sbRet.append("&nbsp;&nbsp;"+nextImg+"");
            }
            if(wnanchor.getLastPage() != -1) {
                sbRet.append("&nbsp;&nbsp;<A HREF='" + replaceURL(RequestURI,RequestURL,"startCount",String.valueOf(wnanchor.getLastPage())) + "'>"+nnextImg+"</A>");
            } else {
                sbRet.append("&nbsp;&nbsp;"+nnextImg+"");
            }
            return sbRet.toString();
        }

	public String replaceURL(String base, String url, String param, String value) {

		String sURL = "";
		if ( url != null && !url.equals("")) {
			if ( url.indexOf(param) < 0 )
				url = url + "&" + param + "=" + value;

			String [] params = url.split("&");
			for ( int idx=0; idx < params.length; idx++ ) {
				if ( params[idx].indexOf(param) >= 0 ) {
					params[idx] = param + "=" + value;
				}
				sURL = sURL + params[idx] ;

				if ( idx + 1 < params.length)
					sURL = sURL + "&" ;
			}

		} else {
				sURL = param + "=" + value;
		}
		sURL = base + "?" + sURL;
		return sURL;

	}        


}
