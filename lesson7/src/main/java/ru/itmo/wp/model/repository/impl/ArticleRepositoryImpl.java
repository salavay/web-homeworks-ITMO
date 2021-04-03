package ru.itmo.wp.model.repository.impl;

import ru.itmo.wp.model.domain.article.Article;
import ru.itmo.wp.model.repository.AbstractRepository;
import ru.itmo.wp.model.repository.ArticleRepository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.List;

public class ArticleRepositoryImpl extends AbstractRepository<Article> implements ArticleRepository {

    private final String saveStatement = "INSERT INTO `Article` (`userId`, `text`, `title`, `hidden`, `creationTime`) VALUES (?, ?, ?, ?, NOW())";
    private final String findByIdStatement = "SELECT * FROM Article WHERE id=?";
    private final String findAllStatement = "SELECT * FROM Article ORDER BY id DESC";
    private final String changeHiddenStatement = "UPDATE Article SET hidden=? WHERE id=?";
    private final String findByUserIdStatement = "SELECT * FROM Article WHERE userId=?";

    private void setSaveStatement(Article article, PreparedStatement preparedStatement) throws SQLException {
        preparedStatement.setLong(1, article.getUserId());
        preparedStatement.setString(2, article.getText());
        preparedStatement.setString(3, article.getTitle());
        preparedStatement.setBoolean(4, article.isHidden());
    }

    private void saveEntitySetter(Article article, ResultSet resultSet) throws SQLException {
        article.setId(resultSet.getLong(1));
        article.setCreationTime(find(article.getId()).getCreationTime());
    }

    private void setFindByIdStatement(Article article, PreparedStatement preparedStatement) throws SQLException {
        preparedStatement.setLong(1, article.getId());
    }

    private void changeHiddenByIdStatementSetter(Article article,
                                                 PreparedStatement preparedStatement) throws SQLException {
        preparedStatement.setBoolean(1, article.isHidden());
        preparedStatement.setLong(2, article.getId());
    }

    private void findByIdStatementSetter(Article article,
                                         PreparedStatement preparedStatement) throws SQLException {
        preparedStatement.setLong(1, article.getUserId());
    }

    @Override
    public void save(Article article) {
        super.save(article, saveStatement, this::setSaveStatement, this::saveEntitySetter);
    }

    @Override
    public Article find(long id) {
        Article article = new Article();
        article.setId(id);
        return super.findBy(article, findByIdStatement, this::setFindByIdStatement);
    }

    @Override
    public List<Article> findAll() {
        return super.findAllEnt(findAllStatement);
    }

    @Override
    public Article changeHiddenById(long id, boolean newHidden) {
        Article article = find(id);
        article.setHidden(newHidden);
        super.update(article, changeHiddenStatement, this::changeHiddenByIdStatementSetter);
        return article;
    }

    @Override
    public List<Article> findByUserId(long userId) {
        Article article = new Article();
        article.setUserId(userId);
        return super.findByPred(article, findByUserIdStatement, this::findByIdStatementSetter);
    }


    @Override
    protected Article toEntity(ResultSetMetaData metaData, ResultSet resultSet) throws SQLException {
        if (!resultSet.next()) {
            return null;
        }
        Article article = new Article();
        for (int i = 1; i <= metaData.getColumnCount(); i++) {
            switch (metaData.getColumnName(i)) {
                case "id":
                    article.setId(resultSet.getLong(i));
                    break;
                case "userId":
                    article.setUserId(resultSet.getLong(i));
                    break;
                case "creationTime":
                    article.setCreationTime(resultSet.getTimestamp(i));
                    break;
                case "text":
                    article.setText(resultSet.getString(i));
                    break;
                case "title":
                    article.setTitle(resultSet.getString(i));
                    break;
                case "hidden":
                    article.setHidden(resultSet.getBoolean(i));
                    break;
                default:
                    // No operations.
            }
        }
        return article;
    }
}
