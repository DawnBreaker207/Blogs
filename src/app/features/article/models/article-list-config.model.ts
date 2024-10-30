export interface ArticleListConfig {
  type: string;
  filters: {
    tags?: string;
    author?: string;
    favorited?: string;
    limit?: number;
    offset?: number;
  };
}
